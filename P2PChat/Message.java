public class Message{

  public enum MessageType{
    NORMAL,LEAVE,CONNECT
  }

  private MessageType _message_type;
  private int _from_id;
  private String _from_hostname;
  private String _data;

  public Message (int _from_id, String data){
    this._from_id = _from_id;
    this._data = data;
  }
  public void set_message_type_NORMAL(){
    this._message_type = MessageType.NORMAL;
  }
  public MessageType get_message_type(){
    return this._message_type;
  }
  public int get_from_id(){
    return this._from_id;
  }
  public String get_from_hostname(){
    return this._from_hostname;
  }
  public String get_data(){
    return this._data;
  }
}
