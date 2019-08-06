## Find out how to access files with and without code blocks. What is the benefit of the code block?

### WITHOUT CODE BLOCK
f = File.open('Leo Tolstoy - War and Peace.txt', 'w')
f << "Well, Prince, so Genoa and Lucca" 
f << " are now just family estates of the Buonapartes."
f.close

### WITH CODE BLOCK
File.open('Leo Tolstoy - War and Peace.txt', 'w') do |f|
  f << "Well, Prince, so Genoa and Lucca" 
  f << " are now just family estates of the Buonapartes."
end

### SUMMARY
When you write to a file without a code block, you must remember to 
call File.close, otherwise you can get a resource leak.

The primary benefit of the code block is that Ruby handles the closing
of the file handler for you. Additionally, the code block makes the 
encapsulation clear.
	
## How would you translate a hash to an array? Can you translate arrays to hashes?
