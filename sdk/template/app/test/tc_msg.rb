require 'msg'
require "test/unit"

class TestMsg < Test::Unit::TestCase
  def test_msg
    o = Msg.new
    expected = 'Hello World!'
    assert_equal(expected, o.msg)
  end
end