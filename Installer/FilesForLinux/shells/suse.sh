#!/bin/bash
#
#
#    /etc/rc.d/init.d/HIS
#
# HIS  This shell script takes care of starting and stopping
#    the HIS daemon.
#
# chkconfig: 2345 99 99
# description: Host Integrity at Startup (HIS) sends a TCG defined Integrity Report on startup..
# processname: hisd

# Source function library.
. /etc/rc.status
rc_reset

JAVA=/usr/bin/java
TROUSERS=/usr/sbin/tcsd
prog="java"
HISD=/OAT/OAT_Standalone.jar
pid_file=/var/run/his.pid
lock_file=/var/lock/subsys/his
log_file=/var/log/OAT.log
RETVAL=0

[ -x ${TROUSERS} ] || exit 0
service tcsd status || echo $"tcsd needs to be running" || exit 0
[ -x ${JAVA} ] || exit 0

HIS_status(){
	if [ -e "$pid_file" ]; then
		pid=$"`cat $pid_file`"
		item=`ps aux | grep "$pid "`
		if [ $"$item" ]; then
			echo $"OAT (pid $pid) is running..."
		else
			echo $"OAT is stopped"
		fi
	else
		echo $"OAT is stopped"
	fi
}

start() {
	#[ -x $HISD ] || exit 5
	[ -f /OAT/OAT.properties ] || exit 6

	echo -n $"Starting $HISD: "
	$JAVA -jar $HISD /OAT/ -d > "$log_file" 2>&1 &
	PID=$!
	RETVAL=$?
	[ "$RETVAL" = 0 ] && touch $lock_file && echo $PID > $pid_file
	echo
	return $RETVAL
}

stop() {
	#[ -x $HISD ] || exit 5
	[ -f /OAT/OAT.properties ] || exit 6
	#echo $pid_file
	if [ -e "$pid_file" ] ; then
		pid=$"`cat $pid_file`"
		kill -9 $pid
		item=`ps aux | grep "$pid\ "`
		#echo $item
		if [ $"$item" ]; then
			echo $"Stopping $HISD"
		else
			echo $"Stopping $HISD"
		fi
	else
		echo $"Stopping $HISD"
	fi
	RETVAL=$?
	# if we are in halt or reboot runlevel kill all running sessions
	# so the HIS connections are closed cleanly
	if [ "x$runlevel" = x0 -o "x$runlevel" = x6 ] ; then
		trap '' TERM
		killall $prog 2>/dev/null
		trap TERM
	fi
	[ "$RETVAL" = 0 ] && rm -f $lock_file && rm -f $pid_file
	echo
}

restart() {
	stop
	start
}

status() {
	HIS_status
	RETURN=$?
}

case "$1" in
	start)
		start
		rc_status -v
		;;
	stop)
		stop
		rc_status -v
		;;
	restart)
		restart
		rc_status -v
		;;
	status)
		status
		;;
	*)
		echo "Usage: his {start|stop|restart|status}"
		exit 1
		;;
esac
exit $?
