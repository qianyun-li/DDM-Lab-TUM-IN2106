* Install Ansible

* Edit Ansible host file, for Ubuntu it can be found in /etc/ansible

* Add at the end:

```
[master]
master-ip

[master:vars]
ansible_ssh_user=ubuntu
ansible_ssh_private_key_file=<path-to-private-key>

[workers]
worker-ips

[workers:vars]
ansible_ssh_user=ubuntu
ansible_ssh_private_key_file=<path-to-private-key>

```

* Store hadoop installation file in a folder data
