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

    def set_type_id (arg):
        self._type_id = arg

    def get_from_id ():
        return self._from_id

    def set_from_id (arg):
        self._from_id = arg

    def get_from_user_id ():
        return self._from_user_id

    def set_from_user_id (arg):
        self._from_user_id = arg

    def get_data ():
        return self._data

    def set_data (arg):
        self._data = arg

    def __init__(self):
        super(Message, self).__init__()
