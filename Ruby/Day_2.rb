#1a. Print the contents of an array of sixteen numbers, four numbers at a time, using just each.
array = (1..16).to_a
count = 1
array.each do |el|
	print(el, ', ')
	puts if count % 4 == 0
	count +=1
end

#1b. Now do the same with each_slice in Enumerable
