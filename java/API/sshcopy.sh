#!/usr/bin/expect
set ip [lindex $argv 0]
set username [lindex $argv 1]
set passwd [lindex $argv 2]
set file [lindex $argv 3]
spawn ssh-copy-id -i $file -l $username $ip
expect {
        "yes/no" { send "yes\r";exp_continue }
        "password:" { send "$passwd\r" }
}
#expect eof
interact