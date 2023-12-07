print("Enter path to store the file:")
path = input()
print("Enter max number:")
max_nr = input()
with open(path,"w") as f:
    for i in range(1,int(max_nr)):
        f.write(str(i)+"\n")
