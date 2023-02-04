nasm -fwin32 "Hello World.asm"
link /subsystem:console /nodefaultlib /entry:main /lib:hernel32.lib "Hello World.obj" "kernel32.Lib"