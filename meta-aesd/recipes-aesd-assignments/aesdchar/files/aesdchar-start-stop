#!/bin/sh

case "$1" in
    start)
	echo "loading scull"
	/usr/bin/aesdchar_load
	;;
    stop)
	echo "removing scull"
	/usr/bin/aesdchar_unload
	;;
    *)
	echo "Usage: $0 {start|stop}"
	exit 1
esac

exit 0
