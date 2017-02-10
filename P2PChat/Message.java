public class Message{

  public enum MessageType{
    Normal, Leave, Connect
  }

  private MessageType _message_type;
  private int _from_id;
  private String _from_hostname;
  private String _data;

  public MessageType get_message_type(){
    return this._message_type;
  }
  public void set_message_type(MessageType type){
    this._message_type = type;
  }
  public int get_from_id(){
    return this._from_id;
  }
  public void set_from_id(int id){
    this._from_id = id;
  }
  public String get_from_hostname(){
    return this._from_hostname;
  }
  public void set_from_hostname(String id){
    this._from_hostname = id;
  }
  public String get_data(){
    return this._data;
  }
  public void set_data(String id){
    this._data = id;
  }


}
