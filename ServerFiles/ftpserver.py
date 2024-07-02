from pyftpdlib.authorizers import DummyAuthorizer
from pyftpdlib.handlers import FTPHandler
from pyftpdlib.servers import FTPServer
#Locally hosted ftp server to test ftp routes

# Create a dummy authorizer with one user
authorizer = DummyAuthorizer()
authorizer.add_user("tanu", "tanu", "/Users/tanupatel", perm="elradfmw")
authorizer.add_anonymous("/Users/tanupatel")

# Create an FTP handler with the specified authorizer
handler = FTPHandler
handler.authorizer = authorizer

# Specify the address and port for the server
server = FTPServer(("0.0.0.0", 2121), handler)

# Start the server
server.serve_forever()