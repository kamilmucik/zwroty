

```bash
rm -rf textextractor-svc.tar
docker save -o ./textextractor-svc.tar registry.hub.docker.com/kamilmucik/textextractor:0.0.1-SNAPSHOT
ls -lath
```

```bash
ansible-playbook textextractor_svc_deploy.yml -i hosts.yml -e "version=0.0.1-SNAPSHOT"  -e "module=textextractor" -e "filename=textextractor-svc.tar"
#rm -rf textextractor-svc.tar
```

```bash
scp textextractor-svc.tar ubuntu@162.19.227.81:/tmp/d3s/
scp textextractor_svc_deploy.sh ubuntu@162.19.227.81:/tmp/d3s/
```
