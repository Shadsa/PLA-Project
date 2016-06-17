COMPILE=ocamlc

all: clean main

main: automate.cmo createur.ml
	$(COMPILE) $? -o $@

automate.cmo: automate.ml automate.cmi
	$(COMPILE) -c $<

automate.cmi: automate.mli
	$(COMPILE) -c $<

clean:
	rm -f *.cmi *.cmo *~
