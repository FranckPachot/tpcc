sudo apt update -y
sudo apt install -y ant ivy
#
ant bootstrap
ant resolve
ant build
#
docker run -dit -p 5433:5433 -p 15433:15433 --hostname yb --name yb yugabytedb/yugabyte yugabyted start --background=false
until docker exec yb postgres/bin/pg_isready -h yb ; do sleep 1 ; done
#
./tpccbenchmark -c config/workload_all.xml --create=true
./tpccbenchmark -c config/workload_all.xml --load=true
./tpccbenchmark -c config/workload_all.xml --execute=true



