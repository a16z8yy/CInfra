# coding:utf-8
require 'active_record'
require 'mysql2'
require 'sinatra'
#require 'bundler'
#Bundler.require
#require 'rss'

# DB設定ファイルの読み込み
ActiveRecord::Base.configurations = YAML.load_file('database.yml')
ActiveRecord::Base.establish_connection(:development)

class User < ActiveRecord::Base
  
end

# 最新トピック10件分を取得
get '/user.json' do
  content_type :json, :charset => 'utf-8'
  topics = User.order("created_at DESC").limit(10)
  topics.to_json(:root => false)
end

# トピック投稿
post '/user' do
  # リクエスト解析
#  reqData = JSON.parse(request.body.read.to_s) 
#  name = reqData['name']
#  mail = reqData['mail']
#  description = reqData['description']
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
  status 202  
end

get "/" do
    erb :zhyMain
end