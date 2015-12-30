require_relative 'msg.rb'

class Index < AppController
  def index(ctx)
    @logger.info("Logger Works")

    ## User scope DB object:
    @db_usr.set("Hello", "World!")
    @db_usr.get("Hello")

    ## Account scope DB object:
    # API same as @db_user (set / get methods).
    # DB object: @db_acc

    o = Response.new
    o.content_type = 'text/html'
    o.template = 'index_template'
    o.map = {'msg'=>'Hello Wonderful World :-)'}
    o
  end
end
