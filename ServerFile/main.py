import socket
import pyaudio
import wave
CHUNK_SIZE = 1792
FORMAT=pyaudio.paInt16
CHANNELS = 1
RATE = 22050
WAVE_OUTPUT_FILENAME = "server_output.wav"
WIDTH = 2
SERVER_IP = "192.168.1.60"
print(SERVER_IP)

wf = wave.open("CantinaBand3.wav")
audio=pyaudio.PyAudio()
stream = audio.open(format=FORMAT, channels=CHANNELS, rate=RATE, output=True, frames_per_buffer=CHUNK_SIZE)

chunk= wf.readframes(CHUNK_SIZE*10)
stream.write(chunk)








with socket.socket(socket.AF_INET, socket.SOCK_STREAM)as server:
	
	server.bind((SERVER_IP, 3000))
	msg=""
	while(server):
		server.listen()
		print("listening...")
		conn,addr = server.accept()
		print(str(addr)+"has connected")
		with conn:
			stepsize=0
			while stepsize<20:
				chunk = conn.recv(CHUNK_SIZE)
				stream.write(chunk)
				stepsize+=1
				print("Client:",len(chunk),stepsize)
			stream.close()
			audio.terminate()

	server.close()
	
