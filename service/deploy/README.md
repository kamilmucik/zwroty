
```bash
cd ..
mvn -pl :return-parcel-docker-server install -PdockerBuild,docker-registry
cd deploy
```

```bash
rm report_env_162.19.227.81_node1.txt
rm rp-server.tar
docker save -o ./rp-server.tar registry.hub.docker.com/kamilmucik/return-parcel-server:2.5.0
```

```bash
ansible-playbook rp_server_deploy.yml -i hosts.yml -e "version=2.5.0"  -e "module=return-parcel-server" -e "filename=rp-server.tar"
```
