require 'sinatra'

get "/" do
    erb :index
end

get '/login' do
    erb :login
end



post '/login' do
   @@menu = "1. dominfo     2. suspend    3. resume"
   @@uname = params[:uname]
   if @@uname == "yyamakawa"
       @@content = "statusList"
       @@message = "statusList ...."
       erb :zhyMenu
   else
       @@content = ""
       @@message = "statusSpec ...."
       erb :zhyMenu
   end     
end