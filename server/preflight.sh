#!/bin/bash
ls -la /
echo "$(env | sort)"
echo "Kernel name: $(uname -s)"
echo "Kernel release: $(uname -r)"
echo "Kernel version: $(uname -v)"
echo "Hostname: $(uname --nodename)"
echo "Hardware Architecture: $(uname --m)"
echo "Operating System: $(uname -o)"

echo "JAVA_HOME: $(java --version)"
echo "Gradle version: $(gradle --version)"