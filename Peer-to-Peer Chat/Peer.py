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
    def set_peer_id(arg):
        self._peer_id = arg
    def get_next_peer():
        return _next_peer
    def set_next_peer(arg):
        self._next_peer = arg
    def get_pev_peer():
        return _pev_peer
    def set_pev_peer(arg):
        self._pev_peer = arg
    def get_user_name():
        return _user_name
    def set_user_name(arg):
        self._user_name = arg
    def get_peer_ip():
        return _peer_ip
    def set_peer_ip(arg):
        self._peer_ip = arg


    def join (ip):
        return 0
    def exit():
        return 0
    def initialize():
        return 0
    def listen():
        return 0
    def disp_message(data):
        return 0
    def broadcast(data):
        return 0


    def __init__(self):
        super(Peer, self).__init__()
