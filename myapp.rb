# coding:utf-8
require 'active_record'
require 'mysql2'
require 'sinatra'
#require 'rss'

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
       erb :logon
   else
       @@name = user.name
       @@mail = user.mail
       erb :zhyMenu
   end
end

get '/logon' do
  erb :logon
end