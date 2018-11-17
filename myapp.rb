# coding:utf-8
require 'active_record'
require 'mysql2'
require 'sinatra'
#require 'rss'
require './virt/zhy_virt'

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

get '/Signin' do
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
end

get '/dominfo' do
end

post '/dominfo' do
end

get '/suspend' do
end

post '/suspend' do
end

get '/resume' do 
end

post '/resume' do 
end

get '/shutdown' do
end

post '/shutdown' do
end

get '/start' do
end

post '/start' do
end

get '/reboot' do
end

post '/reboot' do
end

get '/destroy' do
end

post '/destroy' do
end
