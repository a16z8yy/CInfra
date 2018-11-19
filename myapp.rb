# coding:utf-8
require 'active_record'
require 'mysql2'
require 'sinatra'
#require 'rss'
require './virt/zhy_virt'
require 'securerandom'

# DB設定ファイルの読み込み
ActiveRecord::Base.configurations = YAML.load_file('database.yml')
ActiveRecord::Base.establish_connection(:development)

class User < ActiveRecord::Base
  
end

# 最新トピック10件分を取得
#get '/user.json' do
#  content_type :json, :charset => 'utf-8'
#  topics = User.order("created_at DESC").limit(10)
#  topics.to_json(:root => false)
#end

# トピック投稿
post '/user' do
  # リクエスト解析
  name = params[:name]
  mail = params[:mail]
  description = params[:description]
  # データ保存
  user = User.new
  user.name = name
  user.mail = mail
  user.description = description
  user.save

  # レスポンスコード
#  status 202  
  erb :login
end

get "/" do
    erb :index
end

get '/login' do
    erb :login
end

post '/login' do
   uname = params[:uname]
   user = User.where(:name => uname).first
   if user == nil 
       erb :signin
   else
       @@uname = user.name
       @@content = ["", ""]
       erb :zhyMenu
   end
end

get '/signin' do
  erb :signin
end

get '/statusList' do
    vm = ZhyVirt.new()
    ret = vm.zhyStatusList()
    @@content = ["post", "statusList"]
    @@message = ret[0] + "\n" + ret[1]
    erb :zhyMenu
end

get '/statusSpec' do
    @@content = ["get", "statusSpec"]
    erb :zhyMenu
end

post '/statusSpec' do
    dname = params[:domName]
    vm = ZhyVirt.new()
    ret = vm.zhyStatusVM(dname)
    @@content = ["post", "statusSpec"]
    @@message = ret[0] + "\n" + ret[1]
    erb :zhyMenu
end

get '/dominfo' do
    @@content = ["get", "dominfo"]
    erb :zhyMenu
end

post '/dominfo' do
    dname = params[:domName]
    vm = ZhyVirt.new()
    ret = vm.zhyDomInfo(dname)
    @@content = ["post", "dominfo"]
    @@message = ret[0] + "\n" + ret[1]
    erb :zhyMenu
end

get '/suspend' do
    @@content = ["get", "suspend"]
    erb :zhyMenu
end

post '/suspend' do
    dname = params[:domName]
    vm = ZhyVirt.new()
    ret = vm.zhySuspend(dname)
    @@content = ["post", "suspend"]
    @@message = ret[0] + "\n" + ret[1]
    erb :zhyMenu
end

get '/resume' do 
    @@content = ["get", "resume"]
    erb :zhyMenu
end

post '/resume' do 
    dname = params[:domName]
    vm = ZhyVirt.new()
    ret = vm.zhyResume(dname)
    @@content = ["post", "resume"]
    @@message = ret[0] + "\n" + ret[1]
    erb :zhyMenu
end

get '/shutdown' do
    @@content = ["get", "shutdown"]
    erb :zhyMenu
end

post '/shutdown' do
    dname = params[:domName]
    vm = ZhyVirt.new()
    ret = vm.zhyShutdown(dname)
    @@content = ["post", "shutdown"]
    @@message = ret[0] + "\n" + ret[1]
    erb :zhyMenu
end

get '/start' do
    @@content = ["get", "start"]
    erb :zhyMenu
end

post '/start' do
    dname = params[:domName]
    vm = ZhyVirt.new()
    ret = vm.zhyStart(dname)
    @@content = ["post", "start"]
    @@message = ret[0] + "\n" + ret[1]
    erb :zhyMenu
end

get '/reboot' do
    @@content = ["get", "reboot"]
    erb :zhyMenu
end

post '/reboot' do
    dname = params[:domName].strip
    vm = ZhyVirt.new()
    ret = vm.zhyReboot(dname)
    @@content = ["post", "reboot"]
    @@message = ret[0] + "\n" + ret[1]
    erb :zhyMenu
end

get '/destroy' do
    @@content = ["get", "destroy"]
    erb :zhyMenu
end

post '/destroy' do
    dname = params[:domName].strip
    vm = ZhyVirt.new()
    ret = vm.zhyDestroy(dname)
    @@content = ["post", "destroy"]
    @@message = ret[0] + "\n" + ret[1]
    erb :zhyMenu
end

get '/centos7' do
    @@content = ["get", "centos7"]
    erb :zhyMenu
end

def macAddr 
    mac = [0x52, 0x42, 0x00, Random.rand(0x7f), Random.rand(0xff), Random.rand(0xff)]
    return (["%02x"] * 6).join(":")% mac
end

def uuidGen
    return SecureRandom.uuid
end

post '/centos7' do
    dname = params[:domName].strip
    vm = ZhyVirt.new()
#   vm.zhyCreateVM(dname)
    tid = Thread.new { vm.zhyCreateVM(dname) }
    @@content = ["post", "centos7"]
    @@message = "MAC address : " + macAddr + "\n" + "UUID : " + uuidGen
    erb :zhyMenu
    tid.join
end
