require 'open3'

class ZhyVirt
    def virCmd(cmd)
    #   out, err, sid = open3.capture3(cmd)
        out, err = Open3.capture3(cmd)
        if err == "" 
            return ["OK", out]
        else
            return ["Err", err]
        end
    end
    
    def zhyStatusList()
        cmd = "virsh --connect=qemu:///system list --all"
        return virCmd(cmd)
    end
    
    def zhyStatusVM(vm)
        cmd = "virsh --connect=qemu:///system list --all"
        ret = virCmd(cmd)
        if ret[0] != ""
            out = ret[1]
            out.each_line do |ln|
                if ln.split.include?(vm)
                    return ["OK", ln]
                end
            end
            return ["Err", ": not found!"]
        else
            return ret
        end
    end
    
    def zhyDomInfo(vm)
        cmd = "virsh --connect=qemu:///system dominfo " + vm
        return virCmd(cmd)
    end
    
    def zhySuspend(vm)
        cmd = "virsh --connect=qemu:///system suspend " + vm
        return virCmd(cmd)
    end
    
    def zhyResume(vm)
        cmd = "virsh --connect=qemu:///system resume " + vm
        return virCmd(cmd)
    end
    
    def zhyShutdown(vm)
        cmd = "virsh --connect=qemu:///system shutdown " + vm
        return virCmd(cmd)
    end
    
    def zhyStart(vm)
        cmd = "virsh --connect=qemu:///system start " + vm
        return virCmd(cmd)
    end
    
    def zhyReboot(vm)
        cmd = "virsh --connect=qemu:///system reboot " + vm
        return virCmd(cmd)
    end
    
    def zhyDestroy(vm)
        cmd = "virsh --connect=qemu:///system destroy " + vm
        return virCmd(cmd)
    end
    
    def zhyCreateVM(vm)
        cmd = "virsh --connect qemu:///system define /var/kvm/disk/centos7-tmp.xml"
        ret1 = virCmd(cmd)
        if ret1[0] == "Err"
            return ["Err", "... Define Err"]
        end
        cmd = "virt-clone --connect qemu:///system --original centos7-tmp --name " + vm + " --file /var/kvm/disk/" + vm + ".img"
        ret2 = virCmd(cmd)
        if ret2[0] == "Err"
            return ["Err", "... Clone Err"]
        end
        cmd = "virsh --connect qemu:///system undefine centos7-tmp"
        ret1 = virCmd(cmd)
        if ret1[0] == "Err"
            return ["Err", "... undefine Err"]
        end
        return ret2
    end
end


if __FILE__ == $0
    vm = ZhyVirt.new()
    ret = vm.zhyStatusList()
    puts "Center status:"
    puts ret[0], ret[1]

    i = ""
    while i != "0"
        puts "Input cmd: "
        puts "1. dominfo     2. suspend    3. resume"
        puts "4. shutdown    5. start      6. reboot"
        puts "7. destroy     8. status     0. create"
        puts "0. quit        a. stustlist"
        i = gets.strip
        if i == "0"
            exit!
        end
        puts "Input vm name: "
        input = gets.strip
        if i == "1"
            ret = vm.zhyDomInfo(input)
        elsif i == "2"
            ret = vm.zhySuspend(input)
        elsif i == "3"
            ret = vm.zhyResume(input)
        elsif i == "4"
            ret = vm.zhyShutdown(input)
        elsif i == "5"
            ret = vm.zhyStart(input)
        elsif i == "6"
            ret = vm.zhyReboot(input)
        elsif i == "7"
            ret = vm.zhyDestroy(input)
        elsif i == "8"
            ret = vm.zhyStatusVM(input)
        elsif i == "9"
            tid = Thread.new { vm.zhyCreateVM(input) }
        elsif i == "a"
            ret = vm.zhyStatusList()
        else
            puts "key in 1..9"
            ret["", ""]
        end
        puts ret[0], ret[1]
    end
end