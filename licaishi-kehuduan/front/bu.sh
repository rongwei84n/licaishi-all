npm run build
tar -zcvf 20180220.tar.gz dist/

scp 20180220.tar.gz root@47.97.100.240:/root/deploy/lcs-s/front/
