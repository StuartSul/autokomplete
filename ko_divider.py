from __future__ import unicode_literals
from __future__ import division

# Unicode = 0xAC00 + (Chosung_index * NUM_JOONG * NUM_JONG) + (Joongsung_index * NUM_JONG) + (Jongsung_index)

CHO = (
    u'ㄱ', u'ㄲ', u'ㄴ', u'ㄷ', u'ㄸ', u'ㄹ', u'ㅁ', u'ㅂ', u'ㅃ', u'ㅅ',
    u'ㅆ', u'ㅇ', u'ㅈ', u'ㅉ', u'ㅊ', u'ㅋ', u'ㅌ', u'ㅍ', u'ㅎ'
)

JOONG = (
    u'ㅏ', u'ㅐ', u'ㅑ', u'ㅒ', u'ㅓ', u'ㅔ', u'ㅕ', u'ㅖ', u'ㅗ', u'ㅘ',
    u'ㅙ', u'ㅚ', u'ㅛ', u'ㅜ', u'ㅝ', u'ㅞ', u'ㅟ', u'ㅠ', u'ㅡ', u'ㅢ', u'ㅣ'
)

JONG = (
    u'', u'ㄱ', u'ㄲ', u'ㄳ', u'ㄴ', u'ㄵ', u'ㄶ', u'ㄷ', u'ㄹ', u'ㄺ',
    u'ㄻ', u'ㄼ', u'ㄽ', u'ㄾ', u'ㄿ', u'ㅀ', u'ㅁ', u'ㅂ', u'ㅄ', u'ㅅ',
    u'ㅆ', u'ㅇ', u'ㅈ', u'ㅊ', u'ㅋ', u'ㅌ', u'ㅍ', u'ㅎ'
)

WHITESPACES = (
    u'', u' ', u'\t', u'\n'
)

JAMO = CHO + JOONG + JONG[1:]

NUM_CHO = 19
NUM_JOONG = 21
NUM_JONG = 28

FIRST_HANGUL_UNICODE = 0xAC00  # '가'
LAST_HANGUL_UNICODE = 0xD7A3  # '힣'

def is_hangul_letter(letter):
    unicode = ord(letter)
    if (unicode < FIRST_HANGUL_UNICODE or unicode > LAST_HANGUL_UNICODE) or letter in JAMO:
        return False
    return True

def decompose(letter):
    if letter in WHITESPACES:
        return ''
    elif letter.isupper():
        return letter.lower()
    elif not is_hangul_letter(letter):
        return letter

    unicode = ord(letter) - FIRST_HANGUL_UNICODE

    jong = int(unicode % NUM_JONG)
    unicode /= NUM_JONG
    joong = int(unicode % NUM_JOONG)
    unicode /= NUM_JOONG
    cho = int(unicode)

    return f'{CHO[cho] + JOONG[joong] + JONG[jong]}'

def decompose_sentence(sentence):
    decomposed = ''
    for letter in sentence:
        decomposed += decompose(letter)
    return decomposed
