# Message: Class which defines the message types. Class is instatinated
# when a message needs to be sent to an indiviaul node or the entire chat.

class Message(object):
    """docstring for Message."""

    _type_id = ''
    _from_id = ''
    _from_user_id = ''
    _data = ''

    def get_type_id ():
        return self._type_id

    def set_type_id (in):
        self._type_id = in

    def get_from_id ():
        return self._from_id

    def set_from_id (in):
        self._from_id = in

    def get_from_user_id ():
        return self._from_user_id

    def set_from_user_id (in):
        self._from_user_id = in

    def get_data ():
        return self._data

    def set_data (in):
        self._data = in

    def __init__(self, arg):
        super(Message, self).__init__()
        self.arg = arg
