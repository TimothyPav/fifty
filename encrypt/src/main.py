import base64

# FILE ENCRYPTION

# TODO
# Read from a file
# Write out my own encryption alogrithm
# Write decryption algorithm
# Take arguments from command line
VALIDATION_HEADER = "VALID_KEY"

def delete_and_write(file, s):
    f = open(file, "w") 
    f.seek(0)
    f.truncate()
    f.write(s)
    f.close()

def check_key(content_of_file):
    for i in range(0, len(VALIDATION_HEADER)):
        if content_of_file[i] != VALIDATION_HEADER[i]:
            return False
    return True

def validate_key(key):
    # atleast 8 chars long
    # one special character
    if len(key) < 8:
        print("Key my be atleast 8 characters")
        return False
    check = False
    special_characters = '"!@#$%^&*()-+?_=,<>/"\''
    special_characters_set = set(special_characters)


    for c in key:
        if c.isspace():
            print("Key can not contain whitespace characters")
            return False
        if c in special_characters_set:
            check = True
        
    if not check:
        print("Key must contain atleast 1 special character")
    return check

def encrypt(file, key):
    f = open(file, "r")
    content_of_file = f.read()
    content_of_file = VALIDATION_HEADER + content_of_file
    length_of_chars_in_file = len(content_of_file)

    j = 0
    for i in range(0, length_of_chars_in_file):
        if j >= len(key): j = 0

        content_of_file = content_of_file[:i] + chr(ord(content_of_file[i]) ^ ord(key[j])) + content_of_file[i+1:]
        j += 1
    content_of_file = base64.b64encode(content_of_file.encode()).decode()
    f.close()
    delete_and_write(file, content_of_file)


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

    if not check_key(content_of_file):
        print("Key is invalid")
        return False

    content_of_file = content_of_file[len(VALIDATION_HEADER):]
    f.close()
    delete_and_write(file, content_of_file)
    return True
        

#python3 main.py decrypt test.txt


def main():
    while True:
        print("[1] Encrypt")
        print("[2] Decrypt")
        choice = input("Choice: ")
        if choice != '1' and choice != '2':
            print("Invalid choice. Shutting down...")
            return

        elif choice == '1':
            file = input("File location?: ")
            key = input("Key?: ")
            if not validate_key(key):
                return
            encrypt(file, key)
            print("File Encrypted.")

        elif choice == '2':
            file = input("Encrypted file location?: ")
            key = input("Key?: ")
            status = decrypt(file, key)
            if status: print("File Decrypted.")

        return

if __name__ == "__main__":
    main()
