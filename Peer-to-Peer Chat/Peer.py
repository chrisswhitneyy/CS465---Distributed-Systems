class Peer(object):
    """docstring for Peer."""

    _peer_id = ''
    _next_peer = ''
    _prev_peer = ''
    _user_name = ''
    _peer_ip = ''

    # Getters and setters
    def get_peer_id():
        return _peer_id
    def set_peer_id(in):
        self._peer_id = in
    def get_next_peer():
        return _next_peer
    def set_next_peer(in):
        self._next_peer = in
    def get_pev_peer():
        return _pev_peer
    def set_pev_peer(in):
        self._pev_peer = in
    def get_user_name():
        return _user_name
    def set_user_name(in):
        self._user_name = in
    def get_peer_ip():
        return _peer_ip
    def set_peer_ip(in):
        self._peer_ip = in

    def join (ip):

    def exit():

    def initialize():

    def listen():

    def disp_message(data):

    def broadcast(data):
            


    def __init__(self, arg):
        super(Peer, self).__init__()
        self.arg = arg
