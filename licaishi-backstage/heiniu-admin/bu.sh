npm run build
tar -zcvf heiniu-admin.tar.gz dist/

scp heiniu-admin.tar.gz root@47.97.100.240:/root/deploy/lcs-backstage/front/
