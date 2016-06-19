COMPILE=ocamlc

all: clean main

main: typesAutomate.cmo constructeur.cmo output.cmo createur.ml
	$(COMPILE) $? -o $@

typesAutomate.cmo: typesAutomate.ml typesAutomate.cmi
	$(COMPILE) -c $<

typesAutomate.cmi: typesAutomate.mli
	$(COMPILE) -c $<

constructeur.cmo: constructeur.ml constructeur.cmi
	$(COMPILE) -c $<

constructeur.cmi: constructeur.mli
	$(COMPILE) -c $<

output.cmo: output.ml output.cmi
	$(COMPILE) -c $<

output.cmi: output.mli
	$(COMPILE) -c $<

clean:
	rm -f *.cmi *.cmo *~
