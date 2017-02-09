from Peer import *
from Message import *

def main( ):

    # Prompt user for input
    while True:
        prompt = "Enter 1. Start new chat 2. To Join existing chat:"
        try:
            join_or_new = int(raw_input(prompt))
            break
        except ValueError:
            print 'Invalid Input'
            continue

    # instatinate peer classes
    peer = Peer()

    # initialize chat
    if join_or_new == 1:

        Peer.initialize()

    elif join_or_new == 2:
        # prompt for ip and call join()
        print (2)




if __name__ == "__main__":
    main()
