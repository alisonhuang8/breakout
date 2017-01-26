#High-level design goals
There are three classes: the Game, the Blocks, and the Levels. The main Game class is used to basically compile all of the main features of the game. The end goal was to make sure that the home page would be set up properly, the instructions were displayed if asked, the levels were set up when play had begun, and that scores and times were kept track of.

#Adding new features
Adding new features should probably be done in the Game class since I put everything (basically) in the same class. If it's a feature that needs to be updated with every step (like in the animation), then add a call to a method in the step method, pass it the necessary variables, then write the intended method somewhere else in the code (preferably in the order that it will be called in.

#Justifying the design
I made the decision to build my levels with Rectangles as opposed to the Block objects that I had made with the Block class. This was, in my view, the easiest way to accomplish the things that needed to be done for this game. In hindsight, smarter programming would have meant using the Block objects.

#Ambiguities
I that the blocks would always have the same dimensions, that they would always be separated by a gap, and that they would always begin in the same exact positions. This helped with creating the three levels that were required of me in this project. 