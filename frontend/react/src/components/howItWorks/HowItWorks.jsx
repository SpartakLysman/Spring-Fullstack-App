import {
    Box,
    Container,
    Heading,
    SimpleGrid,
    Icon,
    Text,
    Stack,
    HStack,
    VStack,
    Card, CardBody,
} from '@chakra-ui/react'
import {CheckCircleIcon} from '@chakra-ui/icons'
import SidebarWithHeader from "../shared/SideBar.jsx";

const customFeaturesData = [
    {title: 'Spring Boot', text: 'As a main framework.'},
    {title: 'PostgreSQL', text: 'Database to store all information about the user.'},
    {title: 'Spring Data JPA', text: 'Simplifies database access and CRUD operations.'},
    {title: 'Flyway', text: 'Flyway for seamless database migrations.'},
    {title: 'JDBC', text: 'For efficient database communication.'},
    {title: 'Docker', text: 'For containerization and deployment.'},
    {title: 'AWS', text: 'For cloud-based hosting and scaling.'},
    {title: 'JavaScript & React', text: 'For front-end development.'},
    {title: 'Spring Security 6', text: 'For secure, authenticated applications.'},
    {title: 'TypeScript', text: 'For type-safe, scalable code.'},
    {title: 'Angular', text: 'For powerful, responsive front-end interfaces.'},
    {title: 'Chakra UI', text: 'Used as a main components/templates provider for frontend.'},
];

const HowItWorks = () => {

    return (
        <SidebarWithHeader>
            <Container bg="mediumturquoise"
                       maxW="full"
                       centerContent
                       w={{base: 'full', md: 'full'}}
                       h="1085"
            >
                <Card m={50} bg="darkslategray"
                      boxShadow="0 30px 60px rgba(0, 0, 0, 0.5), 0 15px 30px rgba(0, 0, 0, 0.5)">
                    <CardBody>
                        <Box p={6} w="1300px" h="900px" color={'black'}>
                            <Stack spacing={5} as={Container} maxW={'4xl'} alignItems={'center'} textAlign={'center'}>
                                <Heading fontSize={'4xl'} color={'black'}>How does it works?</Heading>
                                <Text color={'gray.300'} fontSize={'xl'}>
                                    Stunning, responsive front-end interface that flawlessly communicate with robust,
                                    scalable back-end server, all with the use of industry-standard technologies such
                                    as:
                                </Text>
                            </Stack>
                            <Container maxW={'5xl'} mt={20}>
                                <SimpleGrid columns={{base: 1, md: 4, lg: 2}} spacing={58} paddingLeft={45}>
                                    {customFeaturesData.map((feature) => (
                                        <HStack key={feature.id} marginY={-4}>
                                            <Box color={'green.400'} px={5}>
                                                <Icon as={CheckCircleIcon} w="24px" h="24px"/>
                                            </Box>
                                            <VStack align={'start'}>
                                                <Text fontWeight={800} color={'black'}
                                                      fontSize={'xl'}>{feature.title}</Text>
                                                <Text color={'gray.300'} fontSize={'large'}>{feature.text}</Text>
                                            </VStack>
                                        </HStack>
                                    ))}
                                </SimpleGrid>
                            </Container>
                        </Box>
                    </CardBody>
                </Card>
            </Container>
        </SidebarWithHeader>
    )
}

export default HowItWorks;