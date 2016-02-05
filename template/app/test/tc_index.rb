require 'index'
require 'test/unit'

class TestIndex < Test::Unit::TestCase
  def test_index
    o = Index.new
    expected = Response.new
    expected.content_type = 'text/plain'
    expected.template = 'hello_template'
    expected.map = {'msg'=>'Hello Wonderful World :-)'}
    assert_equal(expected.map, o.index({}).map)
  end
end