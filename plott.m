clc;
clear all;
%Чтение массива из файла
img = fopen('arr3.txt');
file = fopen('arr.txt');
file1 = fopen('arr1.txt');
file2 = fopen('arr2.txt');
file3 = fopen('arr5.txt');
% file4 = fopen('arr6.txt');
i = 1;

while ~(feof(img))
    m = fgetl(img);
    if ~isempty(m)
        d(i,:) = str2num(m);
        i=i+1;
    end;
end;
fclose(img);

j = 1;
while ~(feof(file))
    m = fgetl(file);
    if ~isempty(m)
        a(j,:) = str2num(m);
        j=j+1;
    end;
end;
fclose(file);

while ~(feof(file1))
    m = fgetl(file1);
    if ~isempty(m)
        b = str2num(m);
    end;
end;
fclose(file1);

while ~(feof(file2))
    m = fgetl(file2);
    if ~isempty(m)
        c = str2num(m);
    end;
end;
fclose(file2);
qwe = 1
while ~(feof(file3))
    m = fgetl(file3);
    if ~isempty(m)
        h(qwe,:) = str2num(m);
        qwe = qwe+1;
    end;
end;
fclose(file3);

% j = 1;
% while ~(feof(file4))
%     m = fgetl(file4);
%     if ~isempty(m)
%         g(j,:) = str2num(m);
%         j=j+1;
%     end;
% end;
% fclose(file4);

%Параметры окна
line = size(a,1);
column = size(a,2);
object1 = 1 : line * column;
f = figure ();
set (f, 'Position' ,[0 400 600 600],'Name', 'Paint');

for y = 0 : line-1
    for x = 1 : column
       object1(size(a,2) * y + x) = rectangle ('Position', [x-1 line-y-1 1 1], 'FaceColor', 'white' , 'EdgeColor','black' );     
    end;
end;


%Построение изображения
for y = 0 : line-1
    for x = 1 : column
if (d(y+1,x) <=-1)
       set (object1(column * y + x), 'FaceColor', 'black' , 'EdgeColor','black' );
else if (h(y+1,x) == 50)
        set (object1(column * y + x), 'FaceColor', 'blue' , 'EdgeColor','blue' );
else if (a(y+1,x) == 1)
        set (object1(column * y + x), 'FaceColor', 'red' , 'EdgeColor','black' );
    else if (a(y+1,x) ==0)
         set (object1(column * y + x), 'FaceColor', 'white' , 'EdgeColor','black' );
        else if (a(y+1,x) >1)
              double R; R = 1-(3*a(y+1,x)/600);
              double G; G = 1-(5*a(y+1,x)/600);
              double B; B = 1-(1*a(y+1,x)/600);
                set (object1(column * y + x), 'FaceColor', [R G B] , 'EdgeColor','black' );
            end;
end;
end;
end;
end;
end;
end; 
%Построение источника
rectangle ('Position', [110 line-151 1 1], 'FaceColor', 'blue' , 'EdgeColor','black' );
for y = 0 : line-1
    for x = 1 : column
%                  if (g(y+1,x) >= 0)
%         set (object1(column * y + x), 'FaceColor', 'magenta' , 'EdgeColor','magenta' );
%                  end;
        for z = 1 : size(b,2)
            if ( ((y+1) == b(z)+1) && (x == c(z)+1))
                set (object1(column * y + x), 'FaceColor', 'yellow' , 'EdgeColor','black');
           

            end;
        end;
        end;
        end;


