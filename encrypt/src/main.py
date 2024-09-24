import base64
import sys

# FILE ENCRYPTION

# TODO
# Read from a file
# Write out my own encryption alogrithm
# Write decryption algorithm
# Take arguments from command line

def delete_and_write(file, s):
    f = open(file, "w") 
    f.seek(0)
    f.truncate()
    f.write(s)
    f.close()

def encrypt(file, key):
    f = open(file, "r")
    content_of_file = f.read()
    length_of_chars_in_file = len(content_of_file)

    j = 0
    for i in range(0, length_of_chars_in_file):
        if j >= len(key): j = 0

        content_of_file = content_of_file[:i] + chr(ord(content_of_file[i]) ^ ord(key[j])) + content_of_file[i+1:]
        j += 1
    content_of_file = base64.b64encode(content_of_file.encode()).decode()
    f.close()
    delete_and_write(file, content_of_file)
    decrypt(file, key)


def decrypt(file, key):
    f = open(file, "r")
    content_of_file = f.read()
    content_of_file = base64.b64decode(content_of_file).decode()
    length_of_chars_in_file = len(content_of_file)

    j = 0
    for i in range(0, length_of_chars_in_file):
        if j >= len(key): j = 0

        content_of_file = content_of_file[:i] + chr(ord(content_of_file[i]) ^ ord(key[j])) + content_of_file[i+1:]
        j += 1

    f.close()
    delete_and_write(file, content_of_file)
        


def main():
    if len(sys.argv) != 2:
        print("Wrong num of arguemnts")
        return -1
    encrypt(sys.argv[1], "Hello World")

if __name__ == "__main__":
    main()

# python3 main.py test.txt
# key?: 
