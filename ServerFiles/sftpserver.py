import os
import threading
import paramiko
from socket import socket, AF_INET, SOCK_STREAM

# Generate or load SSH host keys
host_key = paramiko.RSAKey.generate(2048)
host_key_path = '/tmp/test_rsa.key'
host_key.write_private_key_file(host_key_path)

# Create SFTP server handler
class SFTPServer(paramiko.ServerInterface):
    def __init__(self):
        self.event = threading.Event()
    
    def check_auth_password(self, username, password):
        if username == "tanu" and password == "tanu":
            return paramiko.AUTH_SUCCESSFUL
        return paramiko.AUTH_FAILED

    def get_allowed_auths(self, username):
        return 'password'
    
    def check_channel_request(self, kind, chanid):
        if kind == 'session':
            return paramiko.OPEN_SUCCEEDED
        return paramiko.OPEN_FAILED_ADMINISTRATIVELY_PROHIBITED
    
    def check_channel_exec_request(self, channel, command):
        return False

# Define SFTP subsystem handler
class SFTPSubsystemHandler(paramiko.SFTPServerInterface):
    def __init__(self, server, *args, **kwargs):
        super(SFTPSubsystemHandler, self).__init__(server, *args, **kwargs)

# Set up SFTP server
def start_sftp_server():
    sock = socket(AF_INET, SOCK_STREAM)
    sock.bind(('0.0.0.0', 2222))
    sock.listen(5)
    print("SFTP server started on port 2222...")

    while True:
        client, addr = sock.accept()
        t = paramiko.Transport(client)
        t.add_server_key(host_key)
        server = SFTPServer()
        
        try:
            t.start_server(server=server)
            
            # Wait for the client to authenticate
            channel = t.accept(20)
            if channel is None:
                continue
            
            print("Client connected:", addr)
            
            # Start SFTP subsystem
            sftp_server = paramiko.SFTPServer(channel, SFTPSubsystemHandler)
            while t.is_active():
                t.join(1)

        except Exception as e:
            print(f"Exception: {e}")
            t.close()
        finally:
            client.close()

if __name__ == "__main__":
    start_sftp_server()
