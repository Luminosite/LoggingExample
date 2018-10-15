function getVersion(){
    version=`grep '^version' build.sbt | sed -e 's/.*"\(.*\)".*/\1/'`
    echo ${version}
}
    
function getName(){
    name=`grep 'id' build.sbt | head -1 | sed -e 's/.*"\(.*\)".*/\1/'`
    echo ${name}
}
