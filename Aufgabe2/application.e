note
	description : "project application root class"
	date        : "$Date$"
	revision    : "$Revision$"

class
	APPLICATION





inherit
	ARGUMENTS

create
	make

feature {NONE} -- Initialization

     acc1: ACCOUNT
     acc2: ACCOUNT
     person1: PERSON

make
        do
        	println("Beginn:")
        	--Standard Operations
        	!!person1.make("Name")
            create acc1.open (person1)
            create acc2.open (person1)
            printAccounts()
            println("Einzahlung:")
            acc2.deposit (30)
            printAccounts()
            println("Auszahlung:")
            acc1.withdraw (10)
            printAccounts()
            println("Ueberweisung:")
            acc1.transfer_from (acc2, 15)
            printAccounts()
            printaccountdetails (acc1)
            acc1.set_current_creditinterest (4)
            acc1.set_current_debitinterest (15)
            acc1.set_current_maxcredit (5)
            printaccountdetails (acc1)
            --acc1.withdraw (30) IDE stuerzt komplett ab.
            rescue
            	println("Rescue")
         end


         println(string: ANY)
         do
            print(string.out + "%N")
         end

         printAccounts()
         do
         	println("Balances: " + acc1.current_balance.out + " | " + acc2.current_balance.out)
         end

         printAccountDetails(account: Account)
         local
         	i: Integer
         do
         	println("  Credit_Interest: " + account.current_creditinterest.out)
         	println("  Debit_Interest: " + account.current_debitinterest.out)
         	println("  Max_Credit: " + account.current_maxcredit.out)
         	println (account.current_owners.count)
         	from i := 0 until i < account.current_owners.count
         	loop
         		i:= i + 1
         		println("    Owner: " + account.current_owners.item (i).name)
         	end
         end
end
