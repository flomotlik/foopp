class
    ACCOUNT

--	Attribute:
--     Zeichnungsberechtigte
--     Kreditrahmen
--     Sollverzinsung/Habenverzinsung
--     Betrag am Konto

--  Operationen
--     Bareinzahlung
--     Barabhebung
--     Ueberweisung
--     Abfragen/Aenderungen

--  Zusicherungen
--     Verzinsung/Kreditrahmen innerhalb vorgegebener grenzen
--     Mindestbetrag von 2

create
	open

feature

    open
        do
        	!!owners.make(0,5)
            set_current_maxcredit (20)
            set_current_debitinterest (10)
            set_current_creditinterest (2)
        end

    add_owner(who: PERSON)
        do
        	print(who.name)
        	owners.put (who, number_of_owners)
        	print(owners.item(number_of_owners).name)
        	number_of_owners := number_of_owners + 1
        end

    deposit (sum: INTEGER)
        do
            add (sum)
        end

    withdraw (sum: INTEGER)
        do
            add (-sum)
        end

    may_withdraw (sum: INTEGER): BOOLEAN
        do
            Result := (balance + max_credit > sum)
        end

    current_balance: INTEGER is
        do
            Result := balance
        end

     current_maxcredit: Integer is
       	do
       	   Result:= max_credit
       	end

     current_creditinterest: Integer is
       	do
       	   Result:= credit_interest
       	end

     current_debitinterest: Integer is
       	do
       	   Result:= debit_interest
       	end

     current_owners: ARRAY [PERSON]
        do
        	Result:= owners
        end

     set_current_maxcredit(value:Integer)
       	do
       	   max_credit := value
       	end

     set_current_creditinterest(value:Integer)
       	do
       	   credit_interest := value
       	end

     set_current_debitinterest(value:Integer)
       	do
       	   debit_interest := value
       	end


    transfer_from(account: ACCOUNT; sum:INTEGER)
    require account.may_withdraw(sum)
        do
        	account.withdraw (sum)
        	deposit (sum)
        end

feature {NONE}

    balance: INTEGER
    max_credit: INTEGER
    debit_interest: INTEGER
    credit_interest: INTEGER
    owners: ARRAY[PERSON]
    number_of_owners: INTEGER


    add (sum: INTEGER)
        do
            balance := balance + sum
        end

invariant
	-max_credit < balance
	max_credit > 0
end
