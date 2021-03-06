# Print the string "Hello world"
puts "Hello world"

# For the string "Hello, Ruby," find the index of the word "Ruby."
puts "Hello, Ruby".index("Ruby")

# Print your name ten times.
10.times{puts 'Rory'}

# Print the string "This is sentence number 1," where the number 1 changes from 1 to 10
(1..10).each {|iteration| puts "This is sentence number #{iteration}"}

# Bonus problem - random number guessing game
secret = rand(10)
guess = -1

while guess != secret
	puts 'What is your guess?'
	guess = gets.to_i
	if guess < secret
		puts 'too low'
	elsif guess > secret
		puts 'too high'
	end
end
puts 'correctamundo!'
