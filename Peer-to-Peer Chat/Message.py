# Message: Class which defines the message types. Class is instatinated
# when a message needs to be sent to an indiviaul node or the entire chat.

class Message(object):
    """docstring for Message."""
    def __init__(self, arg):
        super(Message, self).__init__()
        self.arg = arg
