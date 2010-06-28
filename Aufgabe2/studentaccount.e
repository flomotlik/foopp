note
	description: "Summary description for {STUDENTACCOUNT}."
	author: ""
	date: "$Date$"
	revision: "$Revision$"

class
	STUDENTACCOUNT inherits
	Account
	redefine open end

	feature

		open()
        do
        	!!owners.make(0,1)
            set_current_maxcredit (0)
            set_current_debitinterest ()
            set_current_creditinterest (2)
        end

end
