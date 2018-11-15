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
    
    def zhyStatus()
        cmd = "virsh --connect=qemu:///system list --all"
        return virCmd(cmd)
    end
    
    def zhyStatusSpec(vm)
        cmd = "virsh --connect=qemu:///system list --all"
        ret = virCmd(cmd)
        if ret[0] != ""
            out = ret[1]
            out.each_line do |ln|
                if ln.split.include?(vm)
                    return ln
                end
            end
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
        cmd = "virsh --connect=qemu:///system resume" + vm
        return virCmd(cmd)
    end
    
    def zhyShutdown(vm)
        cmd = "virsh --connect=qemu:///system shutdown" + vm
        return virCmd(cmd)
    end
    
    def zhyStart(vm)
        cmd = "virsh --connect=qemu:///system start" + vm
        return virCmd(cmd)
    end
    
    def zhyReboot(vm)
        cmd = "virsh --connect=qemu:///system reboot" + vm
        return virCmd(cmd)
    end
    
    def zhyDestroy(vm)
        cmd = "virsh --connect=qemu:///system destroy" + vm
        return virCmd(cmd)
    end
end


if __FILE__ == $0
    vm = ZhyVirt.new()
    vm.zhyStatus()
    puts @@message
    input = gets
    vm.zhyStatus(input)
    puts @@message
    vm.zhyDomInfo(input)
    puts @@message
end