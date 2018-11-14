require 'sinatra'

get "/" do
    erb :index
end

get '/login' do
    erb :login
end

post '/login' do
   @uname = params[:uname]
   if @uname == "yyamakawa"
       erb :zhyMain
   else
       erb :login
   end     
end