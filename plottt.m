clc;
clear all;
%Чтение массива из файла
a = fopen('arr4.txt');
file3 = fopen('arr5.txt');
i = 1;
while ~(feof(a))
    m = fgetl(a);
    if ~isempty(m)
        b(i,:) = str2num(m);
        i=i+1;
    end;
end;
fclose(a);
qwe = 1
while ~(feof(file3))
    m = fgetl(file3);
    if ~isempty(m)
        h(qwe,:) = str2num(m);
        qwe = qwe+1;
    end;
end;
fclose(file3);
line = size(b,1);
column = size(b,2);

for y = 0 : line-1
    for x = 1 : column
        if (h(y+1,x)== 50) b(y+1,x) = 0; end;
    end;
end;



u = 1:1:200;
v = 1:1:200;
[x y] = meshgrid (u,v);
surf (x,y,b);