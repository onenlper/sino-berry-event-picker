./compile.sh

java mentionBoundary/OntoNerTest input.txt

cd /users/yzcchen/tool/CRF/CRF++-0.54

./crf_test -m Onto/ner.model /users/yzcchen/chen3/sinoBerryEventPicker/SinoBerryEventPicker/src/input.txt.crf > /users/yzcchen/chen3/sinoBerryEventPicker/SinoBerryEventPicker/src/input.txt.ner

cd /users/yzcchen/chen3/sinoBerryEventPicker/SinoBerryEventPicker/src

java mentionBoundary/MentionBoundaryTest input.txt


cd /users/yzcchen/tool/CRF/CRF++-0.54

./crf_test -m ACE/Mention/timeModel /users/yzcchen/chen3/sinoBerryEventPicker/SinoBerryEventPicker/src/input.txt.crf2 > /users/yzcchen/chen3/sinoBerryEventPicker/SinoBerryEventPicker/src/input.txt.time

./crf_test -m ACE/Mention/valueModel /users/yzcchen/chen3/sinoBerryEventPicker/SinoBerryEventPicker/src/input.txt.crf2 > /users/yzcchen/chen3/sinoBerryEventPicker/SinoBerryEventPicker/src/input.txt.value

./crf_test -m ACE/Mention/Head/model /users/yzcchen/chen3/sinoBerryEventPicker/SinoBerryEventPicker/src/input.txt.crf2 > /users/yzcchen/chen3/sinoBerryEventPicker/SinoBerryEventPicker/src/input.txt.mention

cd /users/yzcchen/chen3/sinoBerryEventPicker/SinoBerryEventPicker/src

