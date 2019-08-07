#1a. Print the contents of an array of sixteen numbers, four numbers at a time, using just each.
array = (1..16).to_a
count = 1
array.each do |el|
	print(el, ', ')
	puts if count % 4 == 0
	count +=1
end

#1b. Now do the same with each_slice in Enumerable

(1..16).each_slice(4) { |a| p a }

#Let the tree initializer accept a nested structure with hashes and arrays.

class Tree
	attr_accessor :children, :node_name
	
	def initialize(structure)
		@node_name=structure.keys.first
		@children=[]
		structure[@node_name].each {|k, v| @children << Tree.new(k => v)}
	end
	
	def visit_all(&block)
		visit &block
		children.each {|c| c.visit_all &block}
	end
	
	def visit(&block)
		block.call self
	end
end

family_tree = Tree.new({'grandpa'=> {'dad'=> {'child 1' => {}, 'child 2' => {}}, 'uncle'=>{'child 3' => {}, 'child 4' => {}}}})
family_tree.visit_all{|node| puts node.node_name}

#Write a simple grep that will print the lines of a file having any occurrences of a 
# phrase anywhere in that line

def grep(pattern, filename)
	File.open(filename) do |f|
		f.each_line.with_index {|line, index| puts "#{index+1} #{line}" if line.match(/(#{pattern})/)}
	end
end

grep "text", "text.txt"