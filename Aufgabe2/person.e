note
	description: "Summary description for {PERSON}."
	author: ""
	date: "$Date$"
	revision: "$Revision$"

class
	PERSON

create
	make

feature

	name: STRING


make (the_name: String)
            -- Assign the account to owner who.
        do
            name := the_name
        end
end
