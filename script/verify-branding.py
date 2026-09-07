"""Check renamed Maven modules and Spring class registrations without a database."""
from pathlib import Path
import re
import xml.etree.ElementTree as ET

root = Path(__file__).resolve().parents[1]
ns = {'m': 'http://maven.apache.org/POM/4.0.0'}
errors = []
for legacy in root.glob('yudao-*'):
    if legacy.is_dir() and any(p.is_file() for p in legacy.rglob('*')):
        errors.append(f'Resources remain in legacy module: {legacy.name}')

required_resources = [
    'yian-framework/yian-spring-boot-starter-biz-ip/src/main/resources/ip2region.xdb',
    'yian-module-infra/src/main/resources/file/erweima.jpg',
    'yian-module-system/src/main/resources/images/jigsaw/original/bg1.png',
    'yian-module-system/src/main/resources/images/jigsaw/slidingBlock/1.png',
    'yian-module-system/src/main/resources/images/pic-click/bg1.png',
]
for resource in required_resources:
    if not (root / resource).is_file():
        errors.append(f'Missing runtime resource: {resource}')

pom_count = registrations = 0
for pom in root.rglob('pom.xml'):
    if 'target' in pom.parts:
        continue
    project = ET.parse(pom).getroot()
    pom_count += 1
    for module in project.findall('m:modules/m:module', ns):
        if not (pom.parent / module.text / 'pom.xml').is_file():
            errors.append(f'Missing module: {pom.relative_to(root)} -> {module.text}')
    for artifact in project.findall('.//m:artifactId', ns):
        if artifact.text and (artifact.text == 'yudao' or artifact.text.startswith('yudao-')):
            errors.append(f'Legacy Maven artifact: {pom.relative_to(root)} -> {artifact.text}')

java_files = [p for p in root.rglob('*.java') if 'target' not in p.parts]
classes = set()
for path in java_files:
    text = path.read_text(encoding='utf-8')
    package = re.search(r'^package\s+([\w.]+);', text, re.M)
    if package:
        classes.add(package.group(1) + '.' + path.stem)
    if re.search(r'\bYudao[A-Za-z0-9_]*', text):
        errors.append(f'Legacy class reference: {path.relative_to(root)}')

for imports in root.rglob('*.imports'):
    if 'target' in imports.parts:
        continue
    for line in imports.read_text(encoding='utf-8').splitlines():
        name = line.strip()
        if name.startswith('cn.iocoder.yudao.'):
            registrations += 1
            if name not in classes:
                errors.append(f'Missing Spring auto-configuration: {name}')

license_text = (root / 'LICENSE').read_text(encoding='utf-8')
for required in ['Copyright (c) 2021 ruoyi-vue-pro',
                 'The above copyright notice and this permission notice shall be included']:
    if required not in license_text:
        errors.append('Required original MIT notice missing')

if errors:
    raise SystemExit('\n'.join(errors))
print(f'PASS: {pom_count} Maven descriptors, {len(java_files)} Java files, '
      f'{registrations} Spring registrations, original MIT notice')
