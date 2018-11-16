require './zhy_virt'

vm = ZhyVirt.new()
vm.zhyStatusList()
puts "Center status:"
puts ret[0], ret[1]

i = ""
while i != "0"
    puts "Input vm name: "
    input = gets.strip
    puts "Input cmd: "
    puts "1. dominfo     2. suspend    3. resume"
    puts "4. shutdown    5. start      6. reboot"
    puts "7. destroy     8. status     0. quit"
    i = gets.split
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
    else
        puts "key in 1..9"
    end
    puts ret[0], ret[1]
end