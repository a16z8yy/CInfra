require 'open3'

class ZhyVirt
    def virCmd(cmd)
    #   out, err, sid = open3.capture3(cmd)
        out, err = open3.capture3(cmd)
        if err == "" 
            @@message = out
            @@error = ""
        else
            @@message = ""
            @@error = err
        end
    end
    
    def zhyStatus()
        cmd = "virsh --connect=qemu:///system list --all"
        virCmd(cmd)
    end
    
    def zhyStatus(vm)
        cmd = "virsh --connect=qemu:///system list --all"
        virCmd(cmd)
        if @@message != ""
            out = @@message
            out.each do |ln|
                if ln.split.include?(vm)
                    @@message = ln
                end
            end
        end
    end
    
    def zhyDomInfo(vm)
        cmd = "virsh --connect=qemu:///system dominfo " + vm
        virCmd(cmd)
    end
    
    def zhySuspend(vm)
        cmd = "virsh --connect=qemu:///system suspend " + vm
        virCmd(cmd)
    end
    
    def zhyResume(vm)
        cmd = "virsh --connect=qemu:///system resume" + vm
        virCmd(cmd)
    end
    
    def zhyShutdown(vm)
        cmd = "virsh --connect=qemu:///system shutdown" + vm
        virCmd(cmd)
    end
    
    def zhyStart(vm)
        cmd = "virsh --connect=qemu:///system start" + vm
        virCmd(cmd)
    end
    
    def zhyReboot(vm)
        cmd = "virsh --connect=qemu:///system reboot" + vm
        virCmd(cmd)
    end
    
    def zhyDestroy(vm)
        cmd = "virsh --connect=qemu:///system destroy" + vm
        virCmd(cmd)
    end
end


if __FILE__ == $0
    
end