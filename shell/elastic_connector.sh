#!/usr/bin/env bash

E_SERVER="localhost"
E_PORT="9200"
E_INDEX="spear"
E_TYPE="dashboard"

base_url="$E_SERVER:$E_PORT"

search(){
curl -X POST -H 'Content-Type: application/json' "$base_url/_search" -d'
  {
     "query": {
       "match_all": {}
     }
  }'
}

elastic_put(){
    local data=$1
    e_put ${E_INDEX} ${E_TYPE} ${data}
}

elastic_get(){
    e_get ${E_INDEX} ${E_TYPE}
}

e_put(){
    local index=$1
    local tp=$2
    local data=$3

    curl -XPOST -H 'Content-Type: application/json' "$base_url/$index/$tp" -d "${data}"
}

e_get(){
    local index=$1
    local tp=$2

 local json_value=`curl -XGET -H 'Content-Type: application/json' "$base_url/$index/$tp/_search" -d '
    {
      "query" : {
        "match_all" : {}
      },
      "sort": [
        {
          "unix_time": {
            "order": "desc"
          }
        }
      ],
      "size": 10000
    }
    '`

    local payload=`echo ${json_value} | jq '.hits.hits'`
    local len=`echo ${payload} | jq '. | length'`
    local ret="["
    for ((i=0;i<$len;i++))
    do
        local record=`echo ${payload} | jq ".[$i]._source"`
        local sep=', '
        if [[ ${i} -eq 0 ]]; then
                sep='\n\t'
        fi
        ret="$ret$sep$record"
    done
    ret="$ret]"
    echo ${ret}
}

date_str=""
ts=0

date_gen(){
    date_str=`date "+%Y-%m-%d %H:%M:%S"`
    sys_check
    case ${machine} in
        Linux)
            ts=`date -d "$date_str" +%s`
            ;;
        Mac)
            ts=`date -j -f "%Y-%m-%d %H:%M:%S" "$date_str" +'%s'`
            ;;
    esac
}

machine=Linux
sys_check(){
    unameOut="$(uname -s)"
    case "${unameOut}" in
        Linux*)     machine=Linux;;
        Darwin*)    machine=Mac;;
        CYGWIN*)    machine=Cygwin;;
        MINGW*)     machine=MinGw;;
        *)          machine="UNKNOWN:${unameOut}"
    esac
}

spear_constructor(){
    if [[ $# != 3 ]]; then
        echo "spear log info needed: <job_name> <step_name> <status>"
        exit 1
    fi

    local job_name=$1
    local step_name=$2
    local status=$3

    date_gen

    echo "{ \"unix_time\":$ts, \"data_time\":\"$date_str\", \"job_name\":\"$job_name\"," \
        "\"step_name\":\"$step_name\",\"status\":\"$status\"}"
}

echo_sample_data(){
echo ' {"unix_time":1545234035,
"data_time":"2018-08-08 23:00:00",
"job_name":"WeeklyJob",
"step_name":"UDS",
"status":"running",
"comment":""}'

}
#search
#echo_data

main(){
#    local data=`spear_constructor "WeeklyJob" "UDS" "finished"`
#    echo "$data"
#    e_put spear dashboard_data "$data"
    e_get spear dashboard_data
}

main
